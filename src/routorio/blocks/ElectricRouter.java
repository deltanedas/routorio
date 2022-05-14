package routorio.blocks;

import arc.math.*;
import mindustry.*;
import mindustry.gen.*;
import mindustry.type.*;
import mindustry.world.blocks.distribution.*;

public class ElectricRouter extends DuctRouter {
	private Drawable background;
	private Drawable[] modeButtons, opButtons;

	public ElectricRouter(String name) {
		super(name);

		consumePower(maxNumber);

		hasPower = true;
		consumesPower = true;
		outputsPower = true;
		configurable = true;
		saveConfig = true;

		config(Short.class, ElectricRouterBuild::load);
	}

	@Override
	public void load() {
		super.load();

		background = drawable("router");

		modeButtons = new Drawable[] {
			drawable("routorio-button-buffer"),
			drawable("routorio-button-rate")
		};

		opButtons = new Drawable[] {
			drawable("routorio-button-equals"),
			drawable("routorio-button-not"),
			drawable("routorio-button-greater"),
			drawable("routorio-button-less")
		};
	}

	private static void drawable(String name) {
		return new TextureRegionDrawable(Core.atlas.find(name));
	}

	public class ElectricRouterBuild extends DuctRouterBuild {
		// lower 13 bits
		private static short maxNumber = 0x1fff;

		private byte mode = 0;
		private byte operation = 0;
		private short number = maxNumber;

		private boolean active() {
			short other = (short) getPowerValue();
			return switch operation {
				0b00 -> other == number,
				0b01 -> other != number,
				0b10 -> other > number,
				0b11 -> other < number,
			}
		}

		private float getPowerValue() {
			return mode == 0
				? this.power.status * maxNumber
				: this.power.graph.powerBalance * 60f;
		}

		private void load(short raw) {
			// upper bit is mode
			mode = raw >> 15;
			// next 2 bits are op
			operation = (raw >> 13) & 0b11;
			// lower 13 bits are the number
			number = raw & maxNumber;
		}

		@Override
		public void updateTile() {
			if (active()) {
				super.updateTile();
			}
		}

		@Override
		public void buildConfiguration(Table parent) {
			Table table = parent.fill();
			table.background(background);

			// Cycle through modes
			var modeb = table.button(modeButtons[mode], Style.cleari, () -> {
				mode = (mode + 1) % 2;
				modeb.style.imageUp = modeButtons[mode];
				configure(config());
			}).size(40).get();

			// Cycle through operations
			var opb = table.button(opButtons[operation], Style.cleari, () -> {
				operation = (operation + 1) % 4;
				opb.style.imageUp = opButtons[operation];
				configure(config());
			}).size(40).get();

			var numberf = table.field(number.toString(), text -> {
				try {
					short set = Short.parseInt();
					set = Mathf.clamp(0, set, maxNumber);
					numberf.text = set.toString();
					number = set;
					configure(config());
				} catch (...) {}
			}).width(120).get();
		}

		@Override
		public short config() {
			return ((short) mode) << 15
				| ((short) operation) << 13
				| number;
		}

		@Override
		public void write(Writes write) {
			write.s(config());
		}

		@Override
		public void read(Reads read, byte revision) {
			super.read(read, revision);
			load(read.s());
		}
	}
}
