/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.itu.frigga.device.model;

import java.io.Serializable;

/**
 *
 * @author phylock
 */
//@Entity
//@Table(name = "function")
public class Function implements Serializable {

  /** ID **/
  //@Id
  //@GeneratedValue
  //@Column(name = "id", unique = true, nullable = false)
  private Long id = null;
  /** Name **/
  //@Column(name = "name", unique = true, nullable = false, length = 30)
  private String name;

  public Function() {
  }

  public Function(String name) {
    this.name = name;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    final Function other = (Function) obj;
    if ((this.name == null) ? (other.name != null) : !this.name.equals(other.name)) {
      return false;
    }
    return true;
  }

  @Override
  public int hashCode() {
    int hash = 5;
    hash = 53 * hash + (this.name != null ? this.name.hashCode() : 0);
    return hash;
  }

  @Override
  public String toString() {
    return "Function{" + "id=" + id + ", name=" + name + '}';
  }
}
