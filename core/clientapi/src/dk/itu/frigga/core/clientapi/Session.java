/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.itu.frigga.core.clientapi;

import java.util.Date;
import java.util.UUID;

/**
 *
 * @author phylock
 */
public class Session {

  public enum Status {

    Valid, Invalid
  };
  private final UUID sessionId;
  private final UUID userId;
  private final String deviceId;
  private final Date initialized;
  private Date last_action;
  private Status status = Status.Valid;

  public Session(UUID userId, String deviceId) {
    sessionId = UUID.randomUUID();
    this.userId = userId;
    this.deviceId = deviceId;
    initialized = new Date();
    last_action = initialized;
  }

  public void invalidate() {
    status = Status.Invalid;
  }

  public UUID getSessionId() {
    return sessionId;
  }

  public String getDeviceId() {
    return deviceId;
  }

  public Date getInitialized() {
    return initialized;
  }

  public Date getLastAction() {
    return last_action;
  }

  public UUID getUserId() {
    return userId;
  }

  public Status getStatus() {
    return status;
  }

  @Override
  public String toString() {
    return "Session{" + "sessionId=" + sessionId + ", userId=" + userId + ", deviceId=" + deviceId + ", initialized=" + initialized + ", last_action=" + last_action + ", status=" + status + '}';
  }


}
