package org.huatekon.utilities;

import java.awt.Point;

import org.powerbot.core.script.job.Task;
import org.powerbot.game.api.methods.Walking;
import org.powerbot.game.api.methods.Widgets;
import org.powerbot.game.api.methods.input.Mouse;
import org.powerbot.game.api.methods.node.GroundItems;
import org.powerbot.game.api.methods.node.SceneEntities;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.methods.widget.Bank;
import org.powerbot.game.api.methods.widget.Camera;
import org.powerbot.game.api.util.Filter;
import org.powerbot.game.api.util.Random;
import org.powerbot.game.api.util.Timer;
import org.powerbot.game.api.wrappers.Entity;
import org.powerbot.game.api.wrappers.Locatable;
import org.powerbot.game.api.wrappers.Tile;
import org.powerbot.game.api.wrappers.node.GroundItem;
import org.powerbot.game.api.wrappers.node.SceneObject;

public class Utilities {

	private static final int ANAGOGIC_ORT = 24909;

	/**
	 * Open a door given {@link SceneObject} != null
	 * 
	 * @param id
	 * @return
	 */
	private static boolean openDoor(final SceneObject door) {
		focus(door);
		if (door.interact("Open")) {
			waitFor(new Condition() {
				@Override
				public boolean validate() {
					return SceneEntities.getAt(door.getLocation()) == null;
				}
			}, 3000);
			return true;
		}
		return false;
	}

	/**
	 * Open a door inside a certain area
	 * 
	 * @param id
	 * @param area
	 * @return <b>true</b> if door was opened, <b>false</b> if it was already
	 *         opened
	 */

	/**
	 * Open a door given location
	 * 
	 * @param location
	 * @return
	 */
	public static boolean openDoor(int id, Tile location) {
		final SceneObject door = SceneEntities.getAt(location);
		if (door != null) {
			openDoor(door);
		}
		return false;
	}

	public static boolean openBank(final SceneObject bank) {
		focus(bank);
		if (Bank.isOpen())
			return true;

		if (!Bank.isOpen()) {
			bank.interact("Bank");
			waitFor(new Condition() {

				@Override
				public boolean validate() {
					return Bank.isOpen();
				}
			}, 3000);
		}
		return false;
	}

	public static boolean waitFor(Condition condition, long timeout) {
		Timer timer = new Timer(timeout);
		while (!condition.validate()) {
			if (!timer.isRunning()) {
				break;
			}
			Task.sleep(200);
		}
		return condition.validate();
	}

	/**
	 * Focus entity if this is not on screen
	 * 
	 * @param entity
	 */
	public static void focus(Locatable entity) {
		if (!((Entity) entity).isOnScreen())
			Camera.turnTo(entity);
	}

	public static void checkRun() {
		if (!Walking.isRunEnabled()) {
			if (Walking.getEnergy() == 100)
				Walking.setRun(true);
		}
	}

	public static void getAnagogic() {
		final GroundItem anagogic = GroundItems.getNearest(ANAGOGIC_ORT);
		if (anagogic != null) {
			focus(anagogic);
			if (!Inventory.isFull()) {
				final int items = Inventory.getCount(true);
				if (anagogic.interact("Take", anagogic.getGroundItem()
						.getName())) {
					waitFor(new Condition() {
						@Override
						public boolean validate() {
							return Inventory.getCount(true) != items;
						}
					}, 3000);
				}
			}
		}
	}

	private static void wiggleMouse() { // adapted from SudoRunespan by
										// Deprecated
		final Point p = Mouse.getLocation();
		Mouse.move(new Point(Random.nextInt(-50, 50) + p.x, Random.nextInt(-50,
				50) + p.y));
	}

	public static void antiban() { // adapted from SudoRunespan by Deprecated
		switch (Random.nextInt(0, 1000)) {
		case 0:
		case 1:
			Camera.setPitch(Random.nextInt(0, 100));
			break;

		case 2:
		case 3:
			Camera.setAngle(Random.nextInt(0, 100));
		case 4:
		case 5:
		case 23:
			SceneObject o = SceneEntities.getNearest(new Filter<SceneObject>() {
				@Override
				public boolean accept(SceneObject sceneObject) {
					return Random.nextInt(0, 10) == 0;
				}
			});

			if (o != null) {
				Camera.turnTo(o);
			}

			break;
		case 24:
			wiggleMouse();
			break;
		default:
			break;
		}
	}

	public static void minActionBar() {
		if (Widgets.get(640, 30).visible()) {
			System.out.println("minising");
			if (Widgets.get(640, 30).interact("Minimise")) {
				Utilities.waitFor(new Condition() {
					@Override
					public boolean validate() {
						return !Widgets.get(640, 30).visible();
					}
				}, 2000);
			}
		}
	}
}
