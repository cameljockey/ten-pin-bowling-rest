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
package com.bowling.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.bowling.exception.InvalidInputException;
import com.bowling.exception.SystemException;
import com.bowling.model.Score;
import com.bowling.service.GameService;
import com.bowling.service.LineService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Represent a Single Player full game for bowling.
 *
 * @author Tejinder Singh.
 */
@Service
public class SinglePlayerGameServiceImpl implements GameService {

	@Autowired
	LineService lineService;

	private static final Logger logger = LoggerFactory.getLogger(SinglePlayerGameServiceImpl.class);

	public List<Score> evaluate(List<String> rollStringList)
			throws SystemException, InvalidInputException {
		logger.info("Entering the SinglePlayerGameServiceImpl evaluation");
		logger.debug("The Roll String List passed in is " + rollStringList);
		try {
			// Start evaluation of game based upon the List of Rolls as String we pass
			List<Score> gameScoreList = new ArrayList<Score>();
			if (rollStringList == null || rollStringList.isEmpty()) {
				// return predefined message score for invalid string
				getGameScoreForInvalidInput(gameScoreList);
				logger.error("The RollString Passed in is Empty Or Null");
				throw new InvalidInputException(
						"No Input for Score was provided. Hence not able to calculate score for game.",
						new IllegalArgumentException());
			} else {
				// return score calculated for valid Roll string
				getGameScoreForValidInput(rollStringList, gameScoreList);
				logger.debug("The output GameService Score List is = " + gameScoreList);
				logger.info("Exiting the SinglePlayerGameServiceImpl evaluation");
				return gameScoreList;
			}
		} catch (Exception e) {
			// throw Exception if at any point there is issue while evaluating Game
			throw new SystemException("Exception while evaluating the Single Player game", e);
		}

	}

	private void getGameScoreForInvalidInput(List<Score> gameScoreList) {
		// return a pre-calculated message we want to pass on in Score
		Score score = new Score();
		score.setMessage("No Input for Score was provided. Hence not able to calculate score for game.");
		score.setSuccess(false);
		gameScoreList.add(score);
	}

	private void getGameScoreForValidInput(List<String> rollStringList,
			List<Score> gameScoreList) throws SystemException {
		try {
			// initialize parameters for score and those we want to pass n to line for
			// calculation.
			Score score = new Score();
			String rollString = rollStringList.get(0);
			List<Integer> scoreList = new ArrayList<Integer>();
			// Calculate the line Score based upon kind of LineService passed
			logger.debug("The Roll String getting passed is " + rollString);
			Integer lineScore = lineService.CalculateLineScore(rollString);
			scoreList.add(lineScore);
			score.setScoreList(scoreList);
			// calculate customized Success message based upon Score range
			score.setMessage(getSuccessMessage(lineScore));
			score.setSuccess(true);
			logger.debug("The output score is " + score);
			// Add to game score list to be returned
			gameScoreList.add(score);
		} catch (Exception e) {
			// throw Exception if at any point there is issue while getting Score for input
			// that is valid
			throw new SystemException("Exception while Calculating Score for an Input that has been determined valid",
					e);
		}
	}

	private String getSuccessMessage(Integer score) {
		// calculate customized Success message based upon Score range
		if (score > 0 && score < 50) {
			return "You suck big time Billie Boy!!";
		} else if (score > 50 && score < 100) {
			return "You need some more practice before you score biggies!!";
		} else if (score > 100 && score < 200) {
			return "You are good but you can do better then that!!";
		} else if (score > 200 && score < 250) {
			return "Buoy Buoy You are a league material!!";
		} else {
			return "Hoorah!!! You are a champion Striker!!!";
		}
	}

}
