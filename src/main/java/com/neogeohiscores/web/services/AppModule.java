package com.neogeohiscores.web.services;

import static org.apache.tapestry5.SymbolConstants.APPLICATION_VERSION;

import java.util.ResourceBundle;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.tapestry5.ioc.Configuration;
import org.apache.tapestry5.ioc.MappedConfiguration;
import org.apache.tapestry5.ioc.OrderedConfiguration;
import org.apache.tapestry5.ioc.Resource;
import org.apache.tapestry5.ioc.ServiceBinder;
import org.apache.tapestry5.ioc.annotations.Contribute;
import org.apache.tapestry5.ioc.internal.services.ResourceSymbolProvider;
import org.apache.tapestry5.ioc.internal.util.ClasspathResource;
import org.apache.tapestry5.ioc.services.SymbolProvider;
import org.apache.tapestry5.ioc.services.SymbolSource;
import org.apache.tapestry5.services.AssetSource;

import com.neogeohiscores.web.services.halloffame.HallOfFameService;
import com.neogeohiscores.web.services.halloffame.HallOfOneCreditService;

public class AppModule {

    public static void contributeFactoryDefaults(MappedConfiguration<String, Object> configuration) {
        String version = ResourceBundle.getBundle("hiscores").getString("version");
        configuration.override(APPLICATION_VERSION, version + "-" + RandomStringUtils.randomNumeric(5));
    }

    public static void contributeComponentMessagesSource(AssetSource assetSource, OrderedConfiguration<Resource> configuration) {
        configuration.add("GlobalCatalogue", assetSource.resourceForPath("hiscores.properties"), "after:AppCatalog");
    }

    public static void contributeHibernateEntityPackageManager(Configuration<String> configuration) {
        configuration.add("com.neogeohiscores.entities");
    }

    public static void bind(ServiceBinder binder) {
        binder.bind(TimelineService.class);
        binder.bind(ScoreService.class);
        binder.bind(TitleUnlockingService.class);
        binder.bind(TitleService.class);
        binder.bind(GameService.class);
        binder.bind(PlayerService.class);
        binder.bind(ChallengeService.class);
        binder.bind(HallOfFameService.class);
        binder.bind(HallOfOneCreditService.class);
        binder.bind(TitleRelockingService.class);
    }

    @Contribute(SymbolSource.class)
    public static void contributeSymbolSource(OrderedConfiguration<SymbolProvider> configuration) {
        configuration.add("neogeo-hiscores properties", new ResourceSymbolProvider(new ClasspathResource("hiscores.properties")));
    }

}
