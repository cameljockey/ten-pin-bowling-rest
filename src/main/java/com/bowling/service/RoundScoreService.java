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
package com.bowling.service;

import com.bowling.model.Roll;

/**
 * <p>
 * Provides the Score calculation feature to {@link com.bowling.service.RoundService}.
 * The interface is designed on SOLID principles for extensibility.
 * </p>
 *
 * @author Tejinder Singh.
 */
public interface RoundScoreService {
	/**
	 * <p>
	 * Interface created to return the Score calculated for a
	 * {@link com.bowling.service.RoundService}. We need next Two
	 * {@link com.bowling.model.Roll}s too in here so that we should calculate Score
	 * for Strike(with two bonus Rolls) and Spare(with one bonus Roll), in which
	 * case Bonus will differ.
	 * </p>
	 * 
	 * @param firstRoll
	 *            has the Roll representation of first {@link com.bowling.model.Roll}
	 * @param secondRoll
	 *            has the Roll representation of second
	 *            {@link com.bowling.model.Roll}
	 * @param thirdRoll
	 *            has the Roll representation of third {@link com.bowling.model.Roll}
	 * @return Integer as Score calculated for {@link com.bowling.service.RoundService}
	 * @since 1.0
	 */
	Integer getScore(Roll firstRoll, Roll secondRoll, Roll thirdRoll);
}
