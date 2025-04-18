using System.Collections.ObjectModel;
using CommunityToolkit.Mvvm.ComponentModel;
using CommunityToolkit.Mvvm.Input;
using PageBook.Models;
using PageBook.Services;

namespace PageBook.ViewModels;
public partial class EnhancedCalendarViewModel : ObservableObject
{
    private readonly EventStorageService _eventService;
    
    [ObservableProperty]
    private DateTime _currentMonth = DateTime.Today;
    
    [ObservableProperty]
    private ObservableCollection<CalendarDay> _days = new();
    
    [ObservableProperty]
    private ObservableCollection<Event> _selectedDayEvents = new();
    
    public EnhancedCalendarViewModel(EventStorageService eventService)
    {
        _eventService = eventService;
        InitializeAsync();
    }
    
    private async void InitializeAsync()
    {
        await GenerateCalendar();
    }
    
    [RelayCommand]
    private async Task PreviousMonth()
    {
        CurrentMonth = CurrentMonth.AddMonths(-1);
        await GenerateCalendar();
    }
    
    [RelayCommand]
    private async Task NextMonth()
    {
        CurrentMonth = CurrentMonth.AddMonths(1);
        await GenerateCalendar();
    }
    
    [RelayCommand]
    private async Task SelectDay(CalendarDay day)
    {
        foreach (var d in Days)
        {
            d.IsSelected = false;
        }
        
        day.IsSelected = true;
        await LoadEventsForDate(day.Date);
    }
    
    private async Task GenerateCalendar()
    {
        Days.Clear();
        
        var firstDayOfMonth = new DateTime(CurrentMonth.Year, CurrentMonth.Month, 1);
        var daysInMonth = DateTime.DaysInMonth(CurrentMonth.Year, CurrentMonth.Month);
        
        // Add days of current month
        for (int i = 0; i < daysInMonth; i++)
        {
            var date = firstDayOfMonth.AddDays(i);
            var events = await _eventService.GetEventsByDateAsync(date);
            
            Days.Add(new CalendarDay
            {
                Date = date,
                HasEvents = events.Any(),
                IsSelected = date.Date == DateTime.Today.Date
            });
        }
        
        if (Days.Any(d => d.IsSelected))
        {
            var selectedDay = Days.First(d => d.IsSelected);
            await LoadEventsForDate(selectedDay.Date);
        }
    }
    
    private async Task LoadEventsForDate(DateTime date)
    {
        var events = await _eventService.GetEventsByDateAsync(date);
        SelectedDayEvents.Clear();
        
        foreach (var eventItem in events)
        {
            SelectedDayEvents.Add(eventItem);
        }
    }
}

public class CalendarDay : ObservableObject
{
    public DateTime Date { get; set; }
    public bool HasEvents { get; set; }
    
    private bool _isSelected;
    public bool IsSelected
    {
        get => _isSelected;
        set => SetProperty(ref _isSelected, value);
    }
}
