package net.engineeringdigest.journalApp.cache;


import net.engineeringdigest.journalApp.entity.ConfigJournalAppEntity;
import net.engineeringdigest.journalApp.repository.ConfigJournalappRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class AppCache {
     public Map<String,String> APP_CACHE ;

     @Autowired
     private ConfigJournalappRepository configJournalappRepository;

     @PostConstruct
     public void init(){
         APP_CACHE = new HashMap<>();
         List<ConfigJournalAppEntity> all = configJournalappRepository.findAll();
         for(ConfigJournalAppEntity configJournalAppEntity:all) {
             APP_CACHE.put(configJournalAppEntity.getKey(), configJournalAppEntity.getValue());
         }
     }
}
