package cs3500.hw02.view;

import cs3500.freecell.view.FreecellView;
import java.io.IOException;

/**
 * Creates a mock FreecellTextView that just throws an IOException in every method.
 */
public class MockFreecellTextView implements FreecellView {

  Appendable log;

  MockFreecellTextView(Appendable log) {
    this.log = log;
  }

  @Override
  public void renderBoard() throws IOException {
    throw new IOException();
  }

  @Override
  public void renderMessage(String message) throws IOException {
    throw new IOException();
  }
}
