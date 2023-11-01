package we.love.casting.spells.CustomHUD;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;

import com.minenash.customhud.HudElements.HudElement;
import com.minenash.customhud.mod_compat.CustomHudRegistry;

import fi.dy.masa.malilib.config.IConfigBoolean;
import fi.dy.masa.malilib.config.IHotkeyTogglable;
import fi.dy.masa.tweakeroo.config.FeatureToggle;

public class TweakerooCompat implements ModInitializer {
	@Override
	public void onInitialize() {
		CustomHudRegistry.registerElement("tweakeroo", (str) -> {
			int index = str.indexOf(':');
			if (index == -1)
				return null;

			String featureStr = str.substring(index + 1);
			if (featureStr != featureStr.toUpperCase()) {
				featureStr = featureStr.replaceAll("([a-z])([A-Z]+)", "$1_$2").toUpperCase();
				// Support {tweakeroo:flySpeed} as a shortened version of {tweakeroo:TWEAK_FLY_SPEED}
				// but don't advertise it, might remove later if we need to support displaying the
				// other options too (not only toggles).
				if (!featureStr.startsWith("TWEAK_")) {
					featureStr = "TWEAK_" + featureStr;
				}
			}

			try {
				FeatureToggle feature = FeatureToggle.valueOf(featureStr);
				return new HudElement() {
					@Override public String getString() { return null; }
					@Override public Number getNumber() { return null; }
					@Override public boolean getBoolean() { return feature.getBooleanValue(); }
				};
			} catch (IllegalArgumentException ignored) { return null; }
		});
	}
}