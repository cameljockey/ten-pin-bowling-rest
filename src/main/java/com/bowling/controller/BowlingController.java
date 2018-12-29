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

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bowling.constants.GameConstants;
import com.bowling.exception.InvalidInputException;
import com.bowling.exception.SystemException;
import com.bowling.model.Score;
import com.bowling.service.GameService;

/**
 * This is the Game controller to run the Game and get the score.
 * 
 * @contextPath /deal
 **/
@RestController
@RequestMapping
public class BowlingController {

	private static final Logger logger = LoggerFactory.getLogger(BowlingController.class);

	@Autowired
	private GameService gameService;

	/**
	 * Get the Score of the bowling Game. 
	 * @param roll is unique. Format: any alphanumeric character to any length
	 * @author Tejinder Singh.
	 * @since 1.0
	 **/
	@RequestMapping(value = GameConstants.GET_SCORE, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<List<Score>> retrieveScoreForSinglePlayerRoll(@PathVariable("roll") String roll)
			throws InvalidInputException, SystemException {
		logger.info("Initializing and starting the application...");
		try {
			List<String> rollStringList = new ArrayList<String>();
			// Add the rolls to a List that will be evaluated. We choose a List so that
			// later if application need to be made for multiplayer then multiple Strings
			// can be passed
			rollStringList.add(roll);
			logger.debug("The roll getting passed is " + rollStringList);
			// Run the GameService to evaluate Score
			List<Score> scoreList = gameService.evaluate(rollStringList);

			return new ResponseEntity<>(scoreList, HttpStatus.OK);
		} catch (InvalidInputException e) {
			// throw Exception if at any point there is issue with Input
			throw new SystemException("The input provided is not valid", e);
		} catch (SystemException e) {
			// throw Exception if at any point there is issue with game application itself
			throw new SystemException(
					"There is an application issue due to which Application was not able to calculate score", e);
		} catch (Exception e) {
			// throw any other uncaught exception
			throw new SystemException("Exception while running the game", e);
		}
	}
}
