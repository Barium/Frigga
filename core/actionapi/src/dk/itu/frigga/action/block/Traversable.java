/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.itu.frigga.action.block;

/**
 *
 * @author phylock
 */
public interface Traversable {

  void traverse(Visitor visitor);
}