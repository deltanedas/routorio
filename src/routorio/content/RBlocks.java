package routorio.content;

import routorio.blocks.*;

import mindustry.content.*;
import mindustry.type.*;
import mindustry.world.*;
import mindustry.world.blocks.production.*;

import static mindustry.type.ItemStack.*;

public class RBlocks {
	public static Block

	// defense
	combatRouter, multiRouter,

	// distribution
	alienRouter, clearRouter, colossus, doubleRouter, explosiveRouter,
	invertedRouter, plasmaRouter, rainbowRouter, reinforcedDoubleRouter,
	xmasRouter,

	// payloads
	routoidAssembler, routoidLiquefactor,

	// power
	arcRouter, electricRouter, fusionRouter, magnetRouter, moderouter,
	phaseRouter, solarRouter,

	// production
	routerMixer,

	// units
	reveroutFabricator, routerpedeChainer;

	public static void load() {
		// power
		/*electricRouter = new ElectricRouter("electric-router") {{
			requirements(Category.power, with(Items.beryllium, 10, Items.tungsten, 15, Items.silicon, 10));
		}};*/

		magnetRouter = new MagnetRouter("magnet-router") {{
			requirements(Category.power, with(Items.silicon, 40, Items.oxide, 10));
			health = 600;
			speed = 4f;
			regionRotated1 = 1;
			solid = false;
			researchCost = with(Items.silicon, 120, Items.oxide, 30);

			sparkChance = 0.1f;
			hasPower = true;
			consumesPower = true;
			conductivePower = true;
			consumePower(0.25f / 60f);
		}};

		// production
		routerMixer = new HeatCrafter("router-mixer") {{
			requirements(Category.crafting, with(Items.silicon, 50, Items.graphite, 40, Items.beryllium, 130, Items.tungsten, 80));
			size = 3;

			researchCostMultiplier = 1.5f;
			craftTime = 10f;
			liquidCapacity = 50f;
			consumePower(240f / 60f);

			heatRequirement = 12f;

			consumeLiquid(Liquids.ozone, 3f / 60f);
			consumeLiquid(RLiquids.liquidRouter, 2f / 60f);

			outputLiquid = new LiquidStack(RLiquids.fusionMix, 5f / 60f);
		}};
	}
}
