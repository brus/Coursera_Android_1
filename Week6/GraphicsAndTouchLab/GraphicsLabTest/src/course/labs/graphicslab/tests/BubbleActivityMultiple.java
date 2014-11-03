package course.labs.graphicslab.tests;

import course.labs.graphicslab.BubbleActivity;

import com.robotium.solo.*;
import android.test.ActivityInstrumentationTestCase2;

public class BubbleActivityMultiple extends
		ActivityInstrumentationTestCase2<BubbleActivity> {
	private Solo solo;

	public BubbleActivityMultiple() {
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
		solo.clickOnScreen(100, 100);

		solo.sleep(1000);
		
		// Assert that a bubble was displayed 
		assertEquals("Bubble hasn't appeared", 1, solo.getCurrentViews(course.labs.graphicslab.BubbleActivity.BubbleView.class).size());

		// Click to create second bubble
		solo.clickOnScreen(500, 500);

		solo.sleep(1000);

		// Assert that a bubble was displayed 
		assertEquals("Second bubble hasn't appeared", 2, solo.getCurrentViews(course.labs.graphicslab.BubbleActivity.BubbleView.class).size());

		solo.sleep(1000);

		// Give misbehaving bubbles a chance to move off screen
		// Assert that there are two bubbles on the screen
		assertEquals(
				"There should be two bubbles on the screen",
				2,
				solo.getCurrentViews(
						course.labs.graphicslab.BubbleActivity.BubbleView.class)
						.size());

	}
}
