using System.Collections.ObjectModel;

using CommunityToolkit.Mvvm.ComponentModel;
using CommunityToolkit.Mvvm.Input;

using PageBook.Models;
using PageBook.Services;

namespace PageBook.ViewModels;

public partial class CalendarViewModel : ObservableObject
{
    private readonly EventStorageService _eventService;
    
    [ObservableProperty]
    private DateTime _selectedDate = DateTime.Today;
    
    [ObservableProperty]
    private ObservableCollection<Event> _events = new();
    
    public CalendarViewModel(EventStorageService eventService)
    {
        _eventService = eventService;
        InitializeAsync();
    }
    
    private async void InitializeAsync()
    {
        await LoadEventsForDate(SelectedDate);
    }
    
    [RelayCommand]
    private async Task DateSelected()
    {
        await LoadEventsForDate(SelectedDate);
    }
    
    [RelayCommand]
    private async Task AddEvent()
    {
        var newEvent = new Event
        {
            Title = "New Event",
            Description = "",
            CreatedAt = SelectedDate,
            ModifiedAt = DateTime.Now,
            Tags = ""
        };
        
        await _eventService.SaveEventAsync(newEvent);
        await LoadEventsForDate(SelectedDate);
    }
    
    [RelayCommand]
    private async Task DeleteEvent(Event eventItem)
    {
        await _eventService.DeleteEventAsync(eventItem.Id);
        await LoadEventsForDate(SelectedDate);
    }
    
    [RelayCommand]
    private async Task UpdateEvent(Event eventItem)
    {
        await _eventService.UpdateEventAsync(eventItem);
    }
    
    private async Task LoadEventsForDate(DateTime date)
    {
        var events = await _eventService.GetEventsByDateAsync(date);
        Events.Clear();
        
        foreach (var eventItem in events)
        {
            Events.Add(eventItem);
        }
    }
}