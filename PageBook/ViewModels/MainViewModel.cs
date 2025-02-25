using System.Collections.ObjectModel;
using System.ComponentModel;
using System.Runtime.CompilerServices;
using System.Windows.Input;

using PageBook.Models;
using PageBook.Services;
using PageBook.Views;

namespace PageBook.ViewModels;

public class MainViewModel : INotifyPropertyChanged
{
    private readonly INavigation navigation;
    private readonly NoteStorageService noteStorageService;
    private ObservableCollection<Note> notes;

    public MainViewModel(INavigation navigation)
    {
        this.navigation = navigation;
        noteStorageService = new NoteStorageService();
        LoadNotesCommand = new Command(async () => await LoadNotesAsync());
        AddNoteCommand = new Command(async () => await AddNoteAsync());
        LoadNotesCommand.Execute(null);
    }
    public MainViewModel()
    {
        
    }

    public ObservableCollection<Note> Notes
    {
        get => notes;
        set => SetProperty(ref notes, value);
    }

    public ICommand LoadNotesCommand { get; }
    public ICommand AddNoteCommand { get; }

    private async Task LoadNotesAsync()
    {
        try
        {
            var loadedNotes = await noteStorageService.GetAllNotesAsync();
            Notes = new ObservableCollection<Note>(loadedNotes);
        }
        catch (Exception ex)
        {
            await Application.Current.MainPage.DisplayAlert(
                "Ошибка", 
                $"Не удалось загрузить заметки: {ex.Message}", 
                "OK");
        }
    }

    private async Task AddNoteAsync()
    {
        await navigation.PushAsync(new EditorPage());
    }

    protected void SetProperty<T>(ref T field, T newValue, [CallerMemberName] string propertyName = null)
    {
        field = newValue;
        PropertyChanged?.Invoke(this, new PropertyChangedEventArgs(propertyName));
    }

    public event PropertyChangedEventHandler PropertyChanged;
}