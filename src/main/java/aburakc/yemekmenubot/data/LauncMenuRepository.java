package aburakc.yemekmenubot.data;

import aburakc.yemekmenubot.data.menu.LaunchMenu;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Date;
import java.util.List;

public interface LauncMenuRepository extends MongoRepository<LaunchMenu, String> {

    public List<LaunchMenu> findByDate(Date date);
    public List<LaunchMenu> findByFormattedDate(long fDate);

}
