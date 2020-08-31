package com.waracle.cakemgr;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CakeApplication.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class CakeRestControllerIT {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    private String getRootUrl() {
        return "http://localhost:" + port;
    }

    @Test
    public void should_listAllCakes_when_rootOfTheServerIsAccessed() throws JsonProcessingException {

        HttpHeaders headers = new HttpHeaders();
        HttpEntity<List<String>> entity = new HttpEntity<>(null, headers);
        ResponseEntity<String> response = restTemplate.exchange(getRootUrl() + "/", HttpMethod.GET, entity, String.class);

        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assert.assertNotNull(response.getBody());

        String cakesSummary = objectMapper.readValue(response.getBody(), String.class);
        Assert.assertNotNull(cakesSummary);

    }

    @Test
    public void should_listAllCakes_when_cakesEndPointIsCalled() {

        HttpHeaders headers = new HttpHeaders();
        HttpEntity<List<String>> entity = new HttpEntity<>(null, headers);
        ResponseEntity<CakeEntity[]> response = restTemplate.exchange(getRootUrl() + "/cakes", HttpMethod.GET, entity, CakeEntity[].class);


        Assert.assertNotNull(response.getBody());

        List<CakeEntity> cakes = Arrays.asList(response.getBody());
        Assert.assertEquals(5, cakes.size());
        verifyInitialSetOfCakes(cakes);

    }

    @Test
    public void should_createACake_when_cakesPostEndPointIsCalled() {

        HttpHeaders headers = new HttpHeaders();
        CakeEntity newCake = new CakeEntity();
        newCake.setTitle("Chocolate Cake");
        newCake.setDesc("Jeff Bezos's Favourite Cake");
        newCake.setImage("https://veenaazmanov.com/wp-content/uploads/2019/08/Chocolate-Birthday-Cake21.jpg");
        HttpEntity<CakeEntity> entity = new HttpEntity<>(newCake, headers);
        ResponseEntity<CakeEntity> response = restTemplate.exchange(getRootUrl() + "/cakes", HttpMethod.POST, entity, CakeEntity.class);

        Assert.assertNotNull(response.getBody());

        CakeEntity createdCake = response.getBody();
        Assert.assertNotNull(createdCake.getId());
        Assert.assertEquals(newCake.getTitle(), createdCake.getTitle());
        Assert.assertEquals(newCake.getDesc(), createdCake.getDesc());
        Assert.assertEquals(newCake.getImage(), createdCake.getImage());

        //Delete the cake created earlier in the test, so that cakes are not modified,
        // which can result in other tests to fail.
        restTemplate.delete(getRootUrl() + "/cakes/" + createdCake.getId());

    }

    private CakeEntity getCakeById(Integer cakeId, List<CakeEntity> cakes) {
        return cakes.stream().filter(cakeEntity -> cakeId.equals(cakeEntity.getId())).findFirst().orElse(null);
    }

    private void verifyInitialSetOfCakes(List<CakeEntity> cakes) {

        //Because the order of the cakes is not guaranteed, traverse the list to find the required cake.
        CakeEntity cake = getCakeById(1, cakes);
        Assert.assertNotNull(cake);
        Assert.assertEquals(new Integer(1), cake.getId());
        Assert.assertEquals("Lemon cheesecake", cake.getTitle());
        Assert.assertEquals("A cheesecake made of lemon", cake.getDesc());
        Assert.assertEquals("https://s3-eu-west-1.amazonaws.com/s3.mediafileserver.co.uk/carnation/WebFiles/RecipeImages/lemoncheesecake_lg.jpg",
                cake.getImage());

        cake = getCakeById(2, cakes);
        Assert.assertNotNull(cake);
        Assert.assertEquals(new Integer(2), cake.getId());
        Assert.assertEquals("victoria sponge", cake.getTitle());
        Assert.assertEquals("sponge with jam", cake.getDesc());
        Assert.assertEquals("http://www.bbcgoodfood.com/sites/bbcgoodfood.com/files/recipe_images/recipe-image-legacy-id--1001468_10.jpg",
                cake.getImage());

        cake = getCakeById(3, cakes);
        Assert.assertNotNull(cake);
        Assert.assertEquals(new Integer(3), cake.getId());
        Assert.assertEquals("Carrot cake", cake.getTitle());
        Assert.assertEquals("Bugs bunnys favourite", cake.getDesc());
        Assert.assertEquals("http://www.villageinn.com/i/pies/profile/carrotcake_main1.jpg", cake.getImage());

        cake = getCakeById(4, cakes);
        Assert.assertNotNull(cake);
        Assert.assertEquals(new Integer(4), cake.getId());
        Assert.assertEquals("Banana cake", cake.getTitle());
        Assert.assertEquals("Donkey kongs favourite", cake.getDesc());
        Assert.assertEquals("http://ukcdn.ar-cdn.com/recipes/xlarge/ff22df7f-dbcd-4a09-81f7-9c1d8395d936.jpg", cake.getImage());

        cake = getCakeById(5, cakes);
        Assert.assertNotNull(cake);
        Assert.assertEquals(new Integer(5), cake.getId());
        Assert.assertEquals("Birthday cake", cake.getTitle());
        Assert.assertEquals("a yearly treat", cake.getDesc());
        Assert.assertEquals("http://cornandco.com/wp-content/uploads/2014/05/birthday-cake-popcorn.jpg", cake.getImage());

    }

}