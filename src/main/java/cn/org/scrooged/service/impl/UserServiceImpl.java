package cn.org.scrooged.service.impl;

import cn.org.scrooged.base.entity.DataTable;
import cn.org.scrooged.base.entity.ResponseBean;
import cn.org.scrooged.base.service.impl.BaseServiceImpl;
import cn.org.scrooged.entity.User;
import cn.org.scrooged.mapper.IUserMapper;
import cn.org.scrooged.service.IUserService;
import cn.org.scrooged.util.CharSequenceUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * @author Design By Scrooged
 * @version 1.0
 * @description
 * @date 2018/8/23 16:47
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class UserServiceImpl extends BaseServiceImpl<IUserMapper, User> implements IUserService {

    @Resource
    private IUserMapper userMapper;

    @Override
    public List<User> selectUser() {
        return userMapper.selectUser();
    }

    @Override
    public User selectByUid(String userId) {
        return selectById(userId);
    }

    @Override
    public boolean delById(String userId) {
        return deleteById(userId);
    }

    @Override
    public boolean modifyById(User user) {
        return updateById(user);
    }

    @Override
    public ResponseBean<User> add(User user) {
        insert(user);
        return new ResponseBean<User>().returnData(user);
    }

    @Override
    public List<User> selectAll() {
        return selectList(null);
    }

    @Override
    public ResponseBean<DataTable<User>> findByPage(DataTable dataTable) {
        return new ResponseBean<DataTable<User>>().returnData(pageSearch(dataTable));
    }

    private Pattern pattern = Pattern.compile("\\$\\{(?<key>.*?)}");

    public String placeHolder(String content, Map<String, String> paramMap){
        LocalDateTime localDateTime = LocalDateTime.now();
        StringBuilder builder = CharSequenceUtils.replaceAll(content, pattern, m ->{
            String key = m.group("key");
            String value = paramMap.get(key);
            if(null != value){
                return value;
            }else if (key.startsWith("date")){
                return DateTimeFormatter.ofPattern(key.length() > "date".length()
                        ? key.substring("date".length() + 1)
                        : "yyyy-MM-dd" )
                        .format(localDateTime);
            }
            return "";
        });
        return builder.toString();
    }
}
