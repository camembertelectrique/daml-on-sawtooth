package com.blockchaintp.sawtooth.daml.processor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.blockchaintp.sawtooth.daml.processor.impl.DamlCommitterImpl;
import com.blockchaintp.sawtooth.daml.processor.impl.DamlTransactionHandler;
import com.digitalasset.daml.lf.engine.Engine;

import sawtooth.sdk.processor.TransactionHandler;
import sawtooth.sdk.processor.TransactionProcessor;

/**
 * A basic Main class for DamlTransactionProcessor.
 * @author scealiontach
 */
public final class DamlTransactionProcessorMain {

  private static final Logger LOGGER = LoggerFactory.getLogger(DamlTransactionProcessorMain.class);

  /**
   * A basic main method for this transaction processor.
   * @param args at this time only one argument the address of the validator
   *             component endpoint, e.g. tcp://localhost:4004
   */
  public static void main(final String[] args) {
    TransactionProcessor transactionProcessor = new TransactionProcessor(args[0]);
    Engine engine = new Engine();
    DamlCommitter committer = new DamlCommitterImpl(engine);
    TransactionHandler handler = new DamlTransactionHandler(committer);
    transactionProcessor.addHandler(handler);
    LOGGER.info("Added handler {}", DamlTransactionHandler.class.getName());
    Thread thread = new Thread(transactionProcessor);
    thread.start();
    try {
      thread.join();
    } catch (InterruptedException exc) {
      LOGGER.warn("TransactionProcessor was interrupted");
    }
  }

  private DamlTransactionProcessorMain() {
    // private constructor for utility class
  }

}
