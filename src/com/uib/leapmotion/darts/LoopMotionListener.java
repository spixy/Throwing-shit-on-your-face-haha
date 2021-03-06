package com.uib.leapmotion.darts;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

import com.leapmotion.leap.*;

class LoopMotionListener extends Listener {
    boolean readyForControl = false;
    int screenWidth;
    int screenHeight;
    Gui gui;

    LoopMotionListener(Gui gui) {
        this.gui = gui;
    }

    public void onConnect(Controller controller) {
        System.out.println("Connected");
        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment()
                .getDefaultScreenDevice();
        screenWidth = gd.getDisplayMode().getWidth();
        screenHeight = gd.getDisplayMode().getHeight();
        System.out.println("Screen Resolution: X: " + screenWidth + ", H: "
                + screenHeight);
        readyForControl = true;
    }

    public void onFrame(Controller controller) {
        Frame frame = controller.frame(); // The latest frame

        if (!readyForControl)
            return;

        Hand rightHand = frame.hands().rightmost();

        if (rightHand != null && rightHand.confidence() > .15)
        {
            Vector velocity = rightHand.palmVelocity();
            System.out.println("Velocity: " + velocity);

            float xSpeed = (velocity.getX() /  12);
            float ySpeed = (velocity.getY() / -12);
            float zSpeed = (velocity.getZ() / 400);
            gui.OnHandChange(xSpeed, ySpeed, zSpeed);
        }
    }
}

