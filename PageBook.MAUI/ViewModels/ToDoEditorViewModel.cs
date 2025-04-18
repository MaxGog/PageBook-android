using System.Collections.ObjectModel;
using System.ComponentModel;
using System.Runtime.CompilerServices;
using System.Windows.Input;

using PageBook.Models;
using PageBook.Services;
using PageBook.Views;

namespace PageBook.ViewModels;

public class ToDoEditorViewModel : INotifyPropertyChanged
{
    private readonly INavigation _navigation;
    private readonly ToDoStorageService _storageService;
    private ToDo _currentItem;
    private string _title;
    private string _saveButtonText;

    [Obsolete]
    public ToDoEditorViewModel(INavigation navigation, ToDo item = null)
    {
        _navigation = navigation;
        _storageService = new ToDoStorageService();
        _currentItem = item ?? new ToDo();
        Title = item != null ? "Редактировать задачу" : "Новая задача";
        SaveButtonText = item != null ? "Сохранить" : "Добавить";
        
        SaveCommand = new Command(async () => await SaveAsync());
        CancelCommand = new Command(async () => await _navigation.PopAsync());
    }

    public string Title 
    { 
        get => _title;
        set => SetProperty(ref _title, value);
    }

    public string SaveButtonText 
    { 
        get => _saveButtonText;
        set => SetProperty(ref _saveButtonText, value);
    }

    public ToDo CurrentItem 
    {
        get => _currentItem;
        set => SetProperty(ref _currentItem, value);
    }

    public ICommand SaveCommand { get; }
    public ICommand CancelCommand { get; }

    [Obsolete]
    private async Task SaveAsync()
    {
        try
        {
            await _storageService.SaveToDoItemAsync(_currentItem);
            await _navigation.PopAsync();
        }
        catch (Exception ex)
        {
            await Application.Current.MainPage.DisplayAlert(
                "Ошибка", 
                $"Не удалось сохранить задачу: {ex.Message}", 
                "OK");
        }
    }

    protected void SetProperty<T>(ref T field, T newValue, [CallerMemberName] string propertyName = null)
    {
        field = newValue;
        PropertyChanged?.Invoke(this, new PropertyChangedEventArgs(propertyName));
    }

    public event PropertyChangedEventHandler PropertyChanged;
}