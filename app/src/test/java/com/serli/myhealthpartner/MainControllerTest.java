package com.serli.myhealthpartner;

import android.content.Context;

import com.serli.myhealthpartner.controller.MainController;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class MainControllerTest {

    @Mock
    Context context;

    MainController controller;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void constructorTest() {

    }

}
