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


/**
 * Interface created to Represent a full LineService for the bowling. Which will
 * further have Turns of two rolls each
 *
 * @author Tejinder Singh.
 */
public interface LineService {
	/**
	 * <p>
	 * To Calculate the Total Score as {@link Integer} By Recreating The Rounds In
	 * Loop and add up individual scores of RoundService
	 * </p>
	 * 
	 * @param rollString
	 *            has a single String containing all {@link com.bowling.model.Roll}
	 *            for a line
	 * @return score as {@link Integer}
	 * @author Tejinder Singh.
	 * @since 1.0
	 */
	Integer CalculateLineScore(String rollString);
}
