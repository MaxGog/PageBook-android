using System.Collections.ObjectModel;
using System.ComponentModel;
using System.Diagnostics;
using System.Runtime.CompilerServices;
using System.Windows.Input;

using PageBook.Models;
using PageBook.Services;
using PageBook.Views;

namespace PageBook.ViewModels;

public class EditorViewModel : INotifyPropertyChanged
{
    private readonly INavigation navigation;
    private readonly NoteStorageService noteStorageService;
    private readonly FormattingService formattingService;
    private readonly Editor editor;
    private string content;
    public string id;
    
    public EditorViewModel(INavigation navigation, Editor editor)
    {
        this.navigation = navigation;
        this.editor = editor;
        noteStorageService = new NoteStorageService();
        formattingService = new FormattingService(editor);
        FormatTextCommand = new Command<string>(ApplyFormatting);
        SaveNoteCommand = new Command(async () => await SaveNoteAsync());
        RemoveNoteCommand = new Command(async () => await RemoveNoteAsync());
    }

    public string Content
    {
        get => content;
        set => SetProperty(ref content, value);
    }

    public ICommand FormatTextCommand { get; }
    public ICommand SaveNoteCommand { get; }
    public ICommand RemoveNoteCommand { get; }

    private void ApplyFormatting(string formatType)
    {
        if (!formattingService.CanApplyFormatting())
            return;
            
        var selectedText = editor.Text.Substring(
            editor.CursorPosition - editor.SelectionLength,
            editor.SelectionLength);
            
        formattingService.ApplyFormatting(selectedText, formatType);
    }

    private async Task SaveNoteAsync()
    {
        try
        {
            var note = new Note
            {
                Title = !string.IsNullOrWhiteSpace(Content) ? 
                    Content.Split('\n').FirstOrDefault() ?? "Без названия" : "Без названия",
                Content = Content,
                CreatedAt = DateTime.Now,
                Id = id
            };
            
            await noteStorageService.SaveNoteAsync(note);
            //MessagingCenter.Send(this, "NoteSaved");
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

    private async Task RemoveNoteAsync()
    {
        try
        {
            var note = new Note
            {
                Title = !string.IsNullOrWhiteSpace(Content) ? 
                    Content.Split('\n').FirstOrDefault() ?? "Без названия" : "Без названия",
                Content = Content,
                CreatedAt = DateTime.Now,
                Id = id
            };
            
            await noteStorageService.DeleteNoteAsync(note.Id);
            await navigation.PopAsync();
        }
        catch (Exception ex)
        {
            await Application.Current.MainPage.DisplayAlert(
                "Ошибка",
                $"Не удалось удалить заметку: {ex.Message}",
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