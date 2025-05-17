package com.example.xiaomicar.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.xiaomicar.entity.BatterySignal;
import com.example.xiaomicar.mapper.BatterySignalMapper;
import com.example.xiaomicar.service.BatterySignalService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class BatterySignalServiceImpl extends ServiceImpl<BatterySignalMapper, BatterySignal> implements BatterySignalService {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    private static final String SIGNAL_CACHE_KEY = "battery:signal:";
    private static final long CACHE_EXPIRE_TIME = 30; // 缓存过期时间（分钟）

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean saveSignal(BatterySignal batterySignal) {
        boolean result = save(batterySignal);
        if (result) {
            // 删除缓存
            String cacheKey = SIGNAL_CACHE_KEY + batterySignal.getVehicleId();
            redisTemplate.delete(cacheKey);
        }
        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean saveBatchSignals(List<BatterySignal> signals) {
        boolean result = saveBatch(signals);
        if (result) {
            // 删除相关车辆的缓存
            signals.stream()
                   .map(BatterySignal::getVehicleId)
                   .distinct()
                   .forEach(vehicleId -> redisTemplate.delete(SIGNAL_CACHE_KEY + vehicleId));
        }
        return result;
    }

    @Override
    public List<BatterySignal> getSignalsByTimeRange(Long vehicleId, LocalDateTime startTime, LocalDateTime endTime) {
        LambdaQueryWrapper<BatterySignal> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(BatterySignal::getVehicleId, vehicleId)
               .between(BatterySignal::getSignalTime, startTime, endTime)
               .orderByDesc(BatterySignal::getSignalTime);
        return list(wrapper);
    }

    @Override
    public BatterySignal getLatestSignal(Long vehicleId) {
        String cacheKey = SIGNAL_CACHE_KEY + vehicleId;
        
        // 尝试从缓存获取
        BatterySignal signal = (BatterySignal) redisTemplate.opsForValue().get(cacheKey);
        if (signal != null) {
            return signal;
        }
        
        // 缓存未命中，从数据库查询
        LambdaQueryWrapper<BatterySignal> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(BatterySignal::getVehicleId, vehicleId)
               .orderByDesc(BatterySignal::getSignalTime)
               .last("LIMIT 1");
        signal = getOne(wrapper);
        
        // 将结果存入缓存
        if (signal != null) {
            redisTemplate.opsForValue().set(cacheKey, signal, CACHE_EXPIRE_TIME, TimeUnit.MINUTES);
        }
        
        return signal;
    }
} 