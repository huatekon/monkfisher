package org.huatekon.monkfisher.utilities;

import java.awt.Point;

import org.huatekon.utilities.Condition;
import org.powerbot.core.script.job.Task;
import org.powerbot.game.api.methods.Tabs;
import org.powerbot.game.api.methods.input.Mouse;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.methods.widget.Bank;
import org.powerbot.game.api.methods.widget.Camera;
import org.powerbot.game.api.util.Random;
import org.powerbot.game.api.util.Timer;
import org.powerbot.game.api.wrappers.interactive.NPC;
import org.powerbot.game.api.wrappers.node.SceneObject;

public class Methods {

	private static void wiggleMouse() { // adapted from SudoRunespan by Deprecated
		final Point p = Mouse.getLocation();
		Mouse.move(new Point(Random.nextInt(-150, 150) + p.x, Random.nextInt(-150,
				150) + p.y));
	}

	public static void antiban() { // adapted from SudoRunespan by Deprecated
		switch (Random.nextInt(0, 100)) {
		case 0:
			break;
		case 1:
			Camera.setPitch(Random.nextInt(0, 100));
			break;

		case 2:
		case 3:
		case 4:
		case 5:
			break;
		case 23:
			if (!Variables.random) {
				Tabs.STATS.open(true);
				Point p = new Point(Random.nextInt(553, 729), Random.nextInt(
						260, 510));
				Mouse.move(p);
				Task.sleep(2400, 4120);
				Variables.random = true;
				Tabs.INVENTORY.open();
			} else {
				Camera.setAngle(Random.nextInt(0, 100));
			}
			break;
		case 24:
				wiggleMouse();
				break;
		default:
			break;
		}
	}
	
	public static void dropGiftBox() {
		if (Inventory.getItem(14664) != null) {
			if (Inventory.getItem(14664).getWidgetChild().interact("Drop")) {
				Timer t = new Timer(3000);
				while (t.isRunning() && Inventory.getCount(14664) > 0) {
					Task.sleep(100);
				}
			}
		}
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
	
	public static void openBank(final SceneObject bank) {
		if (!bank.isOnScreen()) {
			Camera.turnTo(bank);
		}
		if (!Bank.isOpen()) {
			bank.interact("Bank");
			waitFor(new Condition() {		
				@Override
				public boolean validate() {
					return Bank.isOpen();
				}
			}, 3000);
		}
	}
	
	public static void openBank(final NPC bank) {
		if (!bank.isOnScreen()) {
			Camera.turnTo(bank);
		}
		if (!Bank.isOpen()) {
			bank.interact("Bank");
			waitFor(new Condition() {		
				@Override
				public boolean validate() {
					return Bank.isOpen();
				}
			}, 3000);
		}
	}
	
}
