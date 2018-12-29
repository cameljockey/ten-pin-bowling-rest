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

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bowling.exception.SystemException;
import com.bowling.model.Roll;
import com.bowling.service.LineService;
import com.bowling.service.RoundService;
import com.bowling.util.RollCreatorUtil;

/**
 * Represent a Single Player Ten RoundService LineService for bowling.
 *
 * @author Tejinder Singh.
 */
@Service
public class TenRoundLineServiceImpl implements LineService {

	@Autowired
	RoundService roundService;

	private static final Logger logger = LoggerFactory.getLogger(TenRoundLineServiceImpl.class);

	public Integer CalculateLineScore(String rollString) throws SystemException {
		logger.info("Entering the TenRoundLineServiceImpl to calculate LineService Score");
		logger.debug("Calculating the line score for " + rollString);
		try {
			// Split the string into a List of 1-character entries.
			// Prepare the List to host all rolls. Add place for 2 more virtual
			// entries so that we don't have to check for null's for 2nd and 3rd rolls in
			// last round.
			List<Character> rollsCharList = rollString.chars().mapToObj(c -> (char) c).collect(Collectors.toList());

			int totalRollCount = rollsCharList.size();
			logger.debug("Total Roll Count is " + totalRollCount);
			// Add 2 more virtual entries (2nd and 3rd roll in last round.)
			rollsCharList.add(null);
			rollsCharList.add(null);
			// Calculate Total Score By Recreating RoundService In Loops
			int totalScore = CalculateTotalScoreByRecreatingRoundInLoops(rollsCharList, totalRollCount);
			logger.debug("Total Score calculated is " + totalScore);
			logger.info("Exiting the TenRoundLineServiceImpl to calculate LineService Score");
			return totalScore;
		} catch (Exception e) {
			// throw Exception if at any point there is issue with calculating score
			throw new SystemException("Exception while Calculating Line Score for a single Line with Single Roll", e);
		}
	}

	private int CalculateTotalScoreByRecreatingRoundInLoops(List<Character> rollsCharList,
			int totalRollCount) throws SystemException {
		try {
			// Initialize counter variable.
			int totalScore = 0;

			// Start analyzing at first roll.
			int rollPos = 0;

			// initialize bonus as zero to start
			int rollBonus = 0;
			logger.info("Starting Analysis of rolls using a loop in order to recreate the rounds");
			// Analyze the rolls using a loop in order to recreate the rounds.
			do {
				// Next round...

				// Get (possibly) three Rolls of this round.
				Roll firstRoll = RollCreatorUtil.createRollFromChar(rollsCharList.get(rollPos));
				Roll secondRoll = RollCreatorUtil.createRollFromChar(rollsCharList.get(rollPos + 1));
				Roll thirdRoll = RollCreatorUtil.createRollFromChar(rollsCharList.get(rollPos + 2));

				// Add round's score to total score.
				totalScore += roundService.getScore(firstRoll, secondRoll, thirdRoll);

				// Jump the consumed rolls so that we don't process them with round.
				rollPos += roundService.getConsumed(firstRoll, secondRoll);

				rollBonus = roundService.getBonus(firstRoll, secondRoll);
				logger.trace("firstRoll=" + firstRoll + ", secondRoll=" + secondRoll + ", thirdRoll=" + thirdRoll
						+ ", totalScore=" + totalScore + ", rollPos=" + rollPos + ", rollBonus=" + rollBonus);

			} while (totalRollCount - rollBonus > ++rollPos);
			return totalScore;
		} catch (Exception e) {
			// throw Exception if at any point there is issue with calculating score
			throw new SystemException("Exception while Calculating Score while recreating the rounds", e);
		}
	}

}
