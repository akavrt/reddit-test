package com.akavrt.reddit;

import android.arch.lifecycle.ViewModel;

import com.akavrt.reddit.controller.LinkListController;

public class ControllerViewModel extends ViewModel {
    private LinkListController controller;

    public LinkListController getController() {
        return controller;
    }

    public void setController(LinkListController controller) {
        this.controller = controller;
    }
}
