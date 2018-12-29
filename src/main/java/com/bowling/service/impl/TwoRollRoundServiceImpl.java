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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.bowling.exception.SystemException;
import com.bowling.model.Roll;
import com.bowling.service.RoundService;

/**
 * Represents a {@link com.bowling.service.RoundService} having Two
 * {@link com.bowling.model.Roll}s.
 *
 * @author Tejinder Singh.
 */
@Service
public class TwoRollRoundServiceImpl implements RoundService {

	private static final Logger logger = LoggerFactory.getLogger(TwoRollRoundServiceImpl.class);

	@Override
	public Integer getBonus(Roll firstRoll, Roll secondRoll) throws SystemException {
		logger.info("Calculating the bonus");
		logger.trace("To calculate Bonus firstRoll=" + firstRoll + ", secondRoll=" + secondRoll);
		try {
			if (firstRoll.isStrike()) {
				// A strike in first roll gives two roll bonus to user
				return 2;
			} else if (null != secondRoll && secondRoll.isSpare()) {
				// A spare in second roll gives bonus of 1 roll to User
				return 1;
			} else if (firstRoll.ishit()) {
				// In two rolls, the bowler is unable to knock down all pins. In which case he
				// does not get any bonus
				return 0;
			} else {
				return 0;
			}
		} catch (Exception e) {
			// throw Exception if at any point there is issue with calculating Bonus
			throw new SystemException("Exception while Calculating Bonus", e);
		}
	}

	@Override
	public Integer getConsumed(Roll firstRoll, Roll secondRoll) throws SystemException {
		logger.info("Calculating the Consumed");
		logger.trace("To calculate Consumed firstRoll=" + firstRoll + ", secondRoll=" + secondRoll);
		try {
			if (firstRoll.isStrike()) {
				// A strike in first roll consumes zero extra roll
				return 0;
			} else if (null != secondRoll && secondRoll.isSpare()) {
				// A spare in second roll consumes one extra Roll
				return 1;
			} else if (firstRoll.ishit()) {
				// In two rolls, the bowler is unable to knock down all pins. In which case he
				// has consumed one extra roll
				return 1;
			} else {
				return 1;
			}
		} catch (Exception e) {
			// throw Exception if at any point there is issue with calculating Consumed Rolls
			throw new SystemException("Exception while Calculating Consumed Rolls", e);
		}
	}

	@Override
	public Integer getScore(Roll firstRoll, Roll secondRoll, Roll thirdRoll) throws SystemException {
		logger.info("Calculating the Score");
		logger.trace("To calculate Score firstRoll=" + firstRoll + ", secondRoll=" + secondRoll + ", thirdRoll="
				+ thirdRoll);
		try {
			Integer score = 0;
			if (firstRoll.isStrike()) {
				// A strike in first roll: Simply add first roll's score (which is 10)
				// to total number of knocked down pins in next 2 rolls. Note that we're
				// not checking if the second roll is a spare, which in this case would
				// indicate invalid input.
				score = firstRoll.getScore() + secondRoll.getScore() + thirdRoll.getScore();
				return score;
			} else if (null != secondRoll && secondRoll.isSpare()) {
				// A spare in second roll: Simply add 10 to total pins knocked down in
				// next roll. Note that we're not checking if total score of first and
				// second rolls is 10, which would indicate that a spare has occurred but
				// the input didn't indicate that.
				score = secondRoll.getScore() + thirdRoll.getScore();
				return score;
			} else if (firstRoll.ishit()) {
				// In two rolls, the bowler is unable to knock down all pins. Simply add
				// total of knocked down pins in these two rolls. No bonus rolls given.
				score = firstRoll.getScore() + secondRoll.getScore();
				return score;
			} else {
				return score;
			}
		} catch (Exception e) {
			// throw Exception if at any point there is issue with calculating score
			throw new SystemException("Exception while Calculating Score", e);
		}
	}

}
