/*
 * Copyright (C) 2014 Tejinder Singh.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.bowling.controller;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import io.restassured.http.ContentType;

import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.bowling.model.Score;
import com.bowling.service.GameService;


import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;

@RunWith(MockitoJUnitRunner.class)
public class BowlingControllerTest {
	
	@InjectMocks
	private BowlingController bowlingController;
	
	@Mock
	private GameService gameService;
	
	@Test
	public void testRetrieveScoreForSinglePlayerRoll() {
		Score score = new Score();
		List<Integer> scoreList = new ArrayList<Integer>();
		scoreList.add(300);
		score.setScoreList(scoreList);
		score.setMessage("Hoorah!!! You are a champion Striker!!!");
		score.setSuccess(true);
		score.setErrorDetails(null);
		
		List<Score> sList = new ArrayList<Score>();
		sList.add(score);
		
		List<String> strList = new ArrayList<String>();
		strList.add("XXXXXXXXXXXX");
		
		when(gameService.evaluate(strList)).thenReturn(sList);
		
		given().standaloneSetup(bowlingController).when()
		.get("/game/XXXXXXXXXXXX").then().statusCode(200).contentType(ContentType.JSON)
		.body(Matchers.containsString("message")).body(Matchers.containsString("Hoorah!!! You are a champion Striker!!!"));
	}

}
