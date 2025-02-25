using System.Collections.ObjectModel;
using System.ComponentModel;
using System.Runtime.CompilerServices;
using System.Windows.Input;

using PageBook.Models;
using PageBook.Services;

namespace PageBook.ViewModels;

public class EditorViewModel : INotifyPropertyChanged
{
    private readonly INavigation navigation;
    private readonly NoteStorageService noteStorageService;
    private string content;
    private readonly FormattingService formattingService;

    public EditorViewModel(INavigation navigation)
    {
        this.navigation = navigation;
        noteStorageService = new NoteStorageService();
        formattingService = new FormattingService();
        FormatTextCommand = new Command<string>(ApplyFormatting);
        SaveNoteCommand = new Command(async () => await SaveNoteAsync());
    }
    public EditorViewModel()
    {
        
    }

    public string Content
    {
        get => content;
        set => SetProperty(ref content, value);
    }

    public ICommand FormatTextCommand { get; }
    public ICommand SaveNoteCommand { get; }

    private void ApplyFormatting(string formatType)
    {
        var editor = Application.Current.MainPage.FindByName<Editor>("noteEditor");
        if (editor == null || editor.SelectionLength == 0)
            return;

        var selectedText = editor.Text;
        formattingService.ApplyFormatting(selectedText, formatType);
    }

    private async Task SaveNoteAsync()
    {
        try
        {
            var note = new Note
            {
                Title = Content.Split('\n').FirstOrDefault() ?? "Без названия",
                Content = Content,
                CreatedAt = DateTime.Now
            };

            await noteStorageService.SaveNoteAsync(note);
            await navigation.PopAsync();
        }
        catch (Exception ex)
        {
            await Application.Current.MainPage.DisplayAlert(
                "Ошибка", 
                $"Не удалось сохранить заметку: {ex.Message}", 
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