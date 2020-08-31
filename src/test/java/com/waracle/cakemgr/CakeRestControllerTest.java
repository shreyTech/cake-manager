package com.waracle.cakemgr;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CakeRestControllerTest {

    @Mock
    private CakeRepository cakeRepo;

    @InjectMocks
    private CakeRestController cakeController = new CakeRestController();

    private CakeEntity newCake;

    @Before
    public void setUp() {
        newCake = new CakeEntity();
        newCake.setTitle("Chocolate Cake");
        newCake.setDesc("Jeff Bezos's Favourite Cake");
        newCake.setImage("https://veenaazmanov.com/wp-content/uploads/2019/08/Chocolate-Birthday-Cake21.jpg");
    }

    @Test
    public void shouldReturnSummaryOfCakes_when_getCakesIsExecutedFromRoot() {

        when(cakeRepo.findAll()).thenReturn(Arrays.asList(newCake));
        String cakeSummary = cakeController.getAllCakesFromRoot();

        Assert.assertNotNull(cakeSummary);
    }

    @Test
    public void shouldInsertCake_when_createCakeIsCalled() {

        when(cakeRepo.save(newCake)).thenReturn(newCake);
        cakeController.createCake(newCake);

        verify(cakeRepo).save(newCake);
    }

    @Test
    public void shouldReturnAllCakes_when_getAllCakesIsCalled() {

        when(cakeRepo.findAll()).thenReturn(Arrays.asList(newCake));
        ResponseEntity cakeResponse = cakeController.getAllCakes();

        Assert.assertEquals(HttpStatus.OK, cakeResponse.getStatusCode());
        Assert.assertNotNull(cakeResponse.getBody());
        verify(cakeRepo).findAll();

    }

}