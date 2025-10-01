package com.chatapp.observer;

/**
 * Subject interface for the Observer Pattern.
 * Defines the contract for managing observers (subscribers).
 * This demonstrates the Behavioral Design Pattern: Observer Pattern.
 */
public interface ChatSubject {
    
    /**
     * Registers an observer to receive notifications
     * @param observer The observer to register
     */
    void registerObserver(ChatObserver observer);
    
    /**
     * Removes an observer from receiving notifications
     * @param observer The observer to remove
     */
    void removeObserver(ChatObserver observer);
    
    /**
     * Notifies all registered observers of changes
     */
    void notifyObservers();
}
