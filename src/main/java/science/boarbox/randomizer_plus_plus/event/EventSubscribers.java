package science.boarbox.randomizer_plus_plus.event;

import com.google.common.collect.Lists;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.resource.ResourcePackManager;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.SaveProperties;
import science.boarbox.randomizer_plus_plus.RandomizerPlusPlus;

import java.util.Collection;
import java.util.Objects;

/**
 * Class for subscribing to events to be registered on mod init
 */
public final class EventSubscribers {
    private static void onServerStarted(MinecraftServer server) {
        // on initial start, sometimes the randomizer RRP isn't loaded, this code ensures all enabled datapacks get loaded

        ResourcePackManager resourcePackManager = Objects.requireNonNull(server).getDataPackManager();
        SaveProperties saveProperties = server.getSaveProperties();

        Collection<String> enabledPacks = resourcePackManager.getEnabledNames();

        Collection<String> packsToReload = Lists.newArrayList(enabledPacks);

        resourcePackManager.scanPacks();
        Collection<String> disabledPacks = saveProperties.getDataConfiguration().dataPacks().getDisabled();

        // adds any datapacks to packsToReload if they were found after the above rescan
        for (String name : resourcePackManager.getNames()) {
            if (!disabledPacks.contains(name) && !packsToReload.contains(name)) {
                packsToReload.add(name);
            }
        }

        server.reloadResources(packsToReload);
        RandomizerPlusPlus.LOGGER.info("Enabled seed");
    }

    /**
     * Event subscribers registration entrypoint method
     */
    public static void subscribe() {
        ServerLifecycleEvents.SERVER_STARTED.register(EventSubscribers::onServerStarted);
    }
}
