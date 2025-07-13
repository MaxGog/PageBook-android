//
//  NotificationUtility.swift
//  pagebook
//
//  Created by Максим Гоглов on 13.07.2025.
//

import UserNotifications

func scheduleNotification(for task: TodoTask) {
    guard let dueDate = task.dueDate else { return }
    
    let content = UNMutableNotificationContent()
    content.title = "Напоминание: \(task.title)"
    content.body = "Срок выполнения задачи истекает сегодня"
    content.sound = .default
    
    let triggerDate = Calendar.current.dateComponents([.year, .month, .day], from: dueDate)
    let trigger = UNCalendarNotificationTrigger(dateMatching: triggerDate, repeats: false)
    
    let request = UNNotificationRequest(
        identifier: task.id.uuidString,
        content: content,
        trigger: trigger
    )
    
    UNUserNotificationCenter.current().add(request)
}
