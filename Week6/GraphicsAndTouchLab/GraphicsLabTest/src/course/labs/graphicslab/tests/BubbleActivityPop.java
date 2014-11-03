package course.labs.graphicslab.tests;

import course.labs.graphicslab.BubbleActivity;

import com.robotium.solo.*;
import android.test.ActivityInstrumentationTestCase2;

public class BubbleActivityPop extends
		ActivityInstrumentationTestCase2<BubbleActivity> {
	private Solo solo;

	public BubbleActivityPop() {
		super(BubbleActivity.class);
	}

	public void setUp() throws Exception {
		solo = new Solo(getInstrumentation());
		getActivity();
	}

	@Override
	public void tearDown() throws Exception {
		solo.finishOpenedActivities();
	}

	public void testRun() {
		// Wait for activity: 'course.labs.TouchLab.BubbleActivity'
		solo.waitForActivity(course.labs.graphicslab.BubbleActivity.class, 2000);

		// Set Still Mode
		solo.clickOnMenuItem("Still Mode");

		// Click to create a bubble
		solo.clickOnScreen(250, 250);

		solo.sleep(1000);
		
		// Assert that a bubble was displayed 
		assertEquals("Bubble hasn't appeared", 1, solo.getCurrentViews(course.labs.graphicslab.BubbleActivity.BubbleView.class).size());

		// Click to remove the same bubble
		solo.clickOnScreen(250, 250);

		solo.sleep(1000);

		// Assert that there are no more bubbles
		assertEquals(
				"The bubble was not popped",
				0,
				solo.getCurrentViews(course.labs.graphicslab.BubbleActivity.BubbleView.class)
						.size());

	}
}
