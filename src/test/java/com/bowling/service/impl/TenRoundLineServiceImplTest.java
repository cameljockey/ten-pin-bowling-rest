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

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.bowling.service.LineService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TenRoundLineServiceImplTest {
	
	@Autowired
	LineService lineService;

	@Test
    public void testAllRollsFail() {
        assertThat(getScoreFromRollsString("--------------------"), is(0));
    }

    @Test
    public void testFirstBallScores3RestFails() {
        assertThat(getScoreFromRollsString("3-------------------"), is(3));
    }

    @Test
    public void testFirstBallScores8RestFails() {
        assertThat(getScoreFromRollsString("8-------------------"), is(8));
    }

    @Test
    public void testAllRollsScore1() {
        assertThat(getScoreFromRollsString("11111111111111111111"), is(20));
    }

    @Test
    public void testFirstRoundGetsSpare() {
        assertThat(getScoreFromRollsString("4/------------------"), is(10));
    }

    @Test
    public void testFirstRollStrikesRestFails() {
        assertThat(getScoreFromRollsString("X------------------"), is(10));
    }

    @Test
    public void testBonusIfRoundBeforeGetsSpare() {
        assertThat(getScoreFromRollsString("3/3-----------------"), is(16));
    }

    @Test
    public void testWithTwoRoundsHavingPureHitsAndRestFails() {
        assertThat(getScoreFromRollsString("3553----------------"), is(16));
    }

    @Test
    public void testBonusesOfStrike() {
        assertThat(getScoreFromRollsString("X53----------------"), is(26));
    }

    @Test
    public void testPerfectScore() {
        assertThat(getScoreFromRollsString("XXXXXXXXXXXX"), is(300));
    }

    @Test
    public void testAllRoundsGetSpares() {
        assertThat(getScoreFromRollsString("1/8/3/9/5/4/2/7/6/3/3"), is(150));
    }

    private int getScoreFromRollsString(String rolls) {
	    Integer score = lineService.CalculateLineScore(rolls);
        return score;
    }

}
