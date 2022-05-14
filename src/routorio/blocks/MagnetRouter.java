package routorio.blocks;

import arc.math.*;
import mindustry.*;
import mindustry.content.*;
import mindustry.gen.*;
import mindustry.type.*;
import mindustry.world.blocks.distribution.*;

public class MagnetRouter extends DuctRouter {
	public MagnetRouter(String name) {
		super(name);
	}

	public float sparkChance;

	public class MagnetRouterBuild extends DuctRouterBuild {
		@Override
		public void updateTile() {
			// Only route items when at least half power
			if (efficiency >= 0.5f) {
				super.updateTile();
			}
		}

		@Override
		public void handleItem(Building source, Item item) {
			super.handleItem(source, item);

			if (Vars.ui != null && Mathf.chance(sparkChance)) {
				Fx.lancerLaserCharge.at(x + Mathf.range(2f), y + Mathf.range(2f),
					Mathf.random(0f, 360f), Items.surgeAlloy.color);
			}
		}
	}
}
