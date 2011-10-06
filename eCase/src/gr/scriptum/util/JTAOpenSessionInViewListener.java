/* JTAOpenSessionInViewListener.java

	Purpose:
		
	Description:
		
	History:
		Tue Sep  5 10:11:55     2006, Created by henrichen

Copyright (C) 2006 Potix Corporation. All Rights Reserved.

{{IS_RIGHT
	This program is distributed under LGPL Version 3.0 in the hope that
	it will be useful, but WITHOUT ANY WARRANTY.
}}IS_RIGHT
 */
package gr.scriptum.util;

import java.util.List;

import javax.naming.InitialContext;
import javax.transaction.Status;
import javax.transaction.UserTransaction;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.zkoss.zk.ui.Execution;
import org.zkoss.zk.ui.util.ExecutionCleanup;
import org.zkoss.zk.ui.util.ExecutionInit;

public class JTAOpenSessionInViewListener implements ExecutionInit,
		ExecutionCleanup {

	private static final Log log = LogFactory
			.getLog(JTAOpenSessionInViewListener.class);

	private UserTransaction getTransaction() throws Exception {
		return (UserTransaction) new InitialContext()
				.lookup("java:comp/UserTransaction");
	}

	private UserTransaction beginTransaction() throws Exception {
		UserTransaction tx = null;
		try {
			tx = getTransaction();
			tx.begin();
			log.info("Started transaction: " + tx);
		} catch (Exception e) {
			log.error(e);
			throw e;
		}
		return tx;
	}

	private void commitTransaction() throws Exception {
		UserTransaction tx = null;
		try {

			tx = getTransaction();
			if (tx.getStatus() == Status.STATUS_ACTIVE) {
				tx.commit();
				log.info("Committed transaction: " + tx);
			}
		} catch (Exception e) {
			log.error(e);
			throw e;
		}
	}

	private void rollbackTransaction() throws Exception {
		UserTransaction tx = null;
		try {
			tx = getTransaction();
			tx.rollback();
			log.info("Rolled back transaction: " + tx);
		} catch (Exception e) {
			log.error(e);
			throw e;
		}

	}

	private void rollback(Execution exec, Throwable ex) {
		try {
			log.error("Trying to rollback database transaction after exception:"
					+ ex);
			rollbackTransaction();

		} catch (Throwable rbEx) {
			log.error(
					"Could not rollback transaction after exception! Original Exception:\n"
							+ ex, rbEx);
		}
	}

	protected void handleOtherException(Execution exec, Throwable ex) {
		// Rollback only
		rollback(exec, ex);
	}

	// -- ExecutionInit --//
	public void init(Execution exec, Execution parent) {
		if (parent == null) { // the root execution of a servlet request
			try {
				beginTransaction();
			} catch (Exception e) {
				log.error(e);
			}
		}
	}

	// -- ExecutionCleanup --//
	public void cleanup(Execution exec, Execution parent, List errs) {
		if (parent == null) { // the root execution of a servlet request

			if (errs == null || errs.isEmpty()) {

				// Commit and cleanup
				try {
					commitTransaction();
				} catch (Exception e) {
					log.error(e);
					handleOtherException(exec, e);
				}
			}
		} else {
			final Throwable ex = (Throwable) errs.get(0);
			// default implementation log the stacktrace and then
			// rollback
			// the transaction.
			handleOtherException(exec, ex);
		}
	}

}
