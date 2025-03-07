using System.Collections.ObjectModel;
using System.ComponentModel;
using System.Runtime.CompilerServices;
using System.Windows.Input;

using PageBook.Models;
using PageBook.Services;
using PageBook.Views;

namespace PageBook.ViewModels;

public class ToDoListViewModel : INotifyPropertyChanged
{
    private readonly INavigation navigation;
    private readonly ToDoStorageService todoStorageService;
    private ObservableCollection<ToDo> items;
    private bool _isLoading;
    public bool IsLoading
    {
        get => _isLoading;
        set => SetProperty(ref _isLoading, value);
    }
    private bool isInitialized;

    public ToDoListViewModel(INavigation navigation)
    {
        this.navigation = navigation;
        todoStorageService = new ToDoStorageService();
        LoadToDoItemsCommand = new Command(async () => await LoadToDoItemsAsync());
        AddToDoItemCommand = new Command(async () => await AddToDoItemAsync());
        EditToDoItemCommand = new Command<ToDo>(async (item) => await EditToDoItemAsync(item));
        sortNameCommand = new Command(() => SortedByName());
        sortDateCommand = new Command(() => SortedByDate());
        
        _ = LoadToDoItemsAsync();
    }

    private async Task LoadToDoItemsAsync()
    {
        try
        {
            IsLoading = true;
            var loadedItems = await todoStorageService.GetAllToDoItemsAsync();
            Items = new ObservableCollection<ToDo>(loadedItems);
        }
        catch (Exception ex)
        {
            await Application.Current.MainPage.DisplayAlert(
                "Ошибка", 
                $"Не удалось загрузить заметки: {ex.Message}", 
                "OK");
        }
        finally
        {
            IsLoading = false;
        }
    }

    public ObservableCollection<ToDo> Items
    {
        get => items;
        set => SetProperty(ref items, value);
    }

    public ICommand LoadToDoItemsCommand { get; }
    public ICommand AddToDoItemCommand { get; }
    public ICommand EditToDoItemCommand { get; }

    public ICommand sortNameCommand { get; private set; }
    public ICommand sortDateCommand { get; private set; }

    private async Task AddToDoItemAsync()
    {
        if (navigation == null)
        {
            await Application.Current.MainPage.DisplayAlert("Ошибка", "Навигация не инициализирована!", "OK");
            return;
        }

        var editorViewModel = new ToDoEditorViewModel(navigation);
        await navigation.PushAsync(new ToDoEditorPage(editorViewModel));
    }

    public async Task EditToDoItemAsync(ToDo item)
    {
        if (navigation == null)
        {
            await Application.Current.MainPage.DisplayAlert("Ошибка", "Навигация не инициализирована!", "OK");
            return;
        }

        var editorViewModel = new ToDoEditorViewModel(navigation, item);
        await navigation.PushAsync(new ToDoEditorPage(editorViewModel));
    }

    protected void SetProperty<T>(ref T field, T newValue, [CallerMemberName] string propertyName = null)
    {
        field = newValue;
        PropertyChanged?.Invoke(this, new PropertyChangedEventArgs(propertyName));
    }

    public event PropertyChangedEventHandler PropertyChanged;

    private void SortedByName()
    {
        var sortedItems = Items.OrderBy(x => x.Title).ToList();
        Items = new ObservableCollection<ToDo>(sortedItems);
    }
    private void SortedByDate()
    {
        var sortedItems = Items.OrderBy(x => x.CreatedAt).ToList();
        Items = new ObservableCollection<ToDo>(sortedItems);
    }
}