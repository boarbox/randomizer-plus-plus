package science.boarbox.randomizer_plus_plus;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;
import net.minecraft.resource.ResourceType;
import net.minecraft.text.Text;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pers.solid.brrp.v1.RRPEventHelper;
import pers.solid.brrp.v1.api.RuntimeResourcePack;
import science.boarbox.randomizer_plus_plus.event.EventSubscribers;
import science.boarbox.randomizer_plus_plus.util.IdentifierUtil;

public class RandomizerPlusPlus implements ModInitializer, ClientModInitializer {
	public static final String MOD_ID = "randomizer_plus_plus";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static final RuntimeResourcePack RESOURCE_PACK = RandomizerPlusPlus.createResourcePack();

	private static RuntimeResourcePack createResourcePack() {
		var pack = RuntimeResourcePack.create(IdentifierUtil.create("seed"));
		// if not enabled, game will crash when loading into a world more than once, this will just overwrite everything that existed already so we don't really have any final duplicates
		pack.setAllowsDuplicateResource(true);

		pack.setDisplayName(Text.of("Randomizer Seed"));
		pack.setDescription(Text.of("Enables randomizer on a world"));
		return pack;
	}


	@Override
	public void onInitialize() {
		RRPEventHelper.AFTER_VANILLA.registerSidedPack(ResourceType.SERVER_DATA, RESOURCE_PACK);
		EventSubscribers.subscribe();
		RandomizerPlusPlus.LOGGER.info("Mod initialized successfully!");
	}

	@Override
	public void onInitializeClient() {
		EventSubscribers.clientSideSubscribe();
	}
}