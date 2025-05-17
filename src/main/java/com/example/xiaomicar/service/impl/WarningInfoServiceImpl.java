package com.example.xiaomicar.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.xiaomicar.config.RocketMQConfig;
import com.example.xiaomicar.entity.WarningInfo;
import com.example.xiaomicar.mapper.WarningInfoMapper;
import com.example.xiaomicar.mq.WarningMessageProducer;
import com.example.xiaomicar.service.WarningInfoService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class WarningInfoServiceImpl extends ServiceImpl<WarningInfoMapper, WarningInfo> implements WarningInfoService {

    private static final String WARNING_CACHE_KEY = "warning:";
    private static final long CACHE_EXPIRE_TIME = 30; // 缓存过期时间（分钟）

    @Resource
    private RedisTemplate<String, Object> redisTemplate;
    
    @Resource
    private WarningMessageProducer warningMessageProducer;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean generateWarning(WarningInfo warningInfo) {
        // 保存预警信息
        boolean result = save(warningInfo);
        if (result) {
            // 发送预警消息
            warningMessageProducer.sendWarningMessage(warningInfo);
            
            // 更新缓存
            String cacheKey = WARNING_CACHE_KEY + warningInfo.getVehicleId();
            redisTemplate.opsForValue().set(cacheKey, warningInfo, CACHE_EXPIRE_TIME, TimeUnit.MINUTES);
        }
        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean generateBatchWarnings(List<WarningInfo> warningInfos) {
        // 批量保存预警信息
        boolean result = saveBatch(warningInfos);
        if (result) {
            // 批量发送预警消息
            warningMessageProducer.sendBatchWarningMessages(warningInfos);
            
            // 更新缓存
            for (WarningInfo warningInfo : warningInfos) {
                String cacheKey = WARNING_CACHE_KEY + warningInfo.getVehicleId();
                redisTemplate.opsForValue().set(cacheKey, warningInfo, CACHE_EXPIRE_TIME, TimeUnit.MINUTES);
            }
        }
        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean handleWarning(Long id, String handleResult) {
        WarningInfo warningInfo = new WarningInfo();
        warningInfo.setId(id);
        warningInfo.setIsHandled(true);
        warningInfo.setHandleTime(LocalDateTime.now());
        warningInfo.setHandleResult(handleResult);
        return updateById(warningInfo);
    }

    @Override
    public List<WarningInfo> getWarningsByTimeRange(Long vehicleId, LocalDateTime startTime, LocalDateTime endTime, Boolean isHandled) {
        LambdaQueryWrapper<WarningInfo> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(WarningInfo::getVehicleId, vehicleId)
               .between(WarningInfo::getWarningTime, startTime, endTime)
               .eq(isHandled != null, WarningInfo::getIsHandled, isHandled)
               .orderByDesc(WarningInfo::getWarningTime);
        return list(wrapper);
    }

    @Override
    public WarningInfo getLatestWarning(Long vehicleId) {
        String cacheKey = WARNING_CACHE_KEY + vehicleId;
        
        // 尝试从缓存获取
        WarningInfo warning = (WarningInfo) redisTemplate.opsForValue().get(cacheKey);
        if (warning != null) {
            return warning;
        }
        
        // 缓存未命中，从数据库查询
        warning = lambdaQuery()
                .eq(WarningInfo::getVehicleId, vehicleId)
                .orderByDesc(WarningInfo::getWarningTime)
                .last("LIMIT 1")
                .one();
        
        // 将结果存入缓存
        if (warning != null) {
            redisTemplate.opsForValue().set(cacheKey, warning, CACHE_EXPIRE_TIME, TimeUnit.MINUTES);
        }
        
        return warning;
    }
} 