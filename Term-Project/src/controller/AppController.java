package controller;

import model.UserSession;
import view.MainFrame;

public class AppController {
    private UserSession session;
    private MainFrame mainFrame;
    private int currentStep;

    public AppController() {
        session = new UserSession();
        currentStep = 0;
        mainFrame = new MainFrame(this);
        mainFrame.setVisible(true);
    }

    public UserSession getSession() {
        return session;
    }

    public int getCurrentStep() {
        return currentStep;
    }

    public void goToStep(int step) {
        this.currentStep = step;
        mainFrame.showStep(step);
    }

    public void nextStep() {
        goToStep(currentStep + 1);
    }

    public void previousStep() {
        if (currentStep > 0)
            goToStep(currentStep - 1);
    }

    public void markStepCompleted(int step) {
        mainFrame.markStepCompleted(step);
    }
}
