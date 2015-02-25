/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.kth.integration;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

/**
 *
 * @author AMore
 */
@TransactionAttribute(TransactionAttributeType.MANDATORY)
@Stateless
public class PersonDAO {

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
}
