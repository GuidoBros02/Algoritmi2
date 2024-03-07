package upo.graph.test;

import static org.junit.jupiter.api.Assertions.*;
import upo.graph.impl.*;
import org.junit.jupiter.api.Test;
import java.util.Arrays;

class GreedyTest 
{
	@Test
	void testGetMaxDisjointIntervals() 
	{
		Integer[] start= {1,3,6};
		Integer[] end= {5,7,9};	
		Integer[] intervals= {0,2};
					
		assertTrue(Arrays.equals(intervals, Greedy.getMaxDisjointIntervals(start, end)));
	}
}