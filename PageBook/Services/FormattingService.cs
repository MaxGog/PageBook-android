using System.Diagnostics;

namespace PageBook.Services;

public class FormattingService
{
    private readonly Dictionary<string, Action<string>> formattingCommands;
    private readonly Editor editor;
    
    public FormattingService(Editor editor)
    {
        this.editor = editor ?? throw new ArgumentNullException(nameof(editor));
        formattingCommands = new Dictionary<string, Action<string>>
        {
            { "bold", text => ApplyFormatting(text, FontAttributes.Bold) },
            { "italic", text => ApplyFormatting(text, FontAttributes.Italic) },
            { "underline", text => ApplyFormatting(text, TextDecorations.Underline) }
        };
    }
    
    public bool CanApplyFormatting()
    {
        return editor != null && 
               editor.SelectionLength > 0;
    }
    
    public void ApplyFormatting(string selectedText, string formatType)
    {
        if (string.IsNullOrEmpty(selectedText))
            return;

        try
        {
            if (formattingCommands.TryGetValue(formatType, out var formatter))
            {
                formatter(selectedText);
            }
        }
        catch (ArgumentOutOfRangeException ex)
        {
            Debug.WriteLine($"Ошибка при форматировании: {ex.Message}");
            // Исправляем позицию курсора
            editor.CursorPosition = Math.Max(0, Math.Min(editor.CursorPosition, editor.Text.Length));
        }
    }
    
    private void ApplyFormatting(string text, FontAttributes attributes)
    {
        try
        {
            var span = new Span
            {
                Text = text,
                FontAttributes = attributes
            };
            
            var currentText = editor.Text;
            var selectionStart = editor.CursorPosition;
            var selectionLength = editor.SelectionLength;
            
            // Проверяем границы
            selectionStart = Math.Max(0, Math.Min(selectionStart, currentText.Length));
            selectionLength = Math.Min(selectionLength, currentText.Length - selectionStart);
            
            editor.Text = currentText.Substring(0, selectionStart) +
                        span.Text +
                        currentText.Substring(selectionStart + selectionLength);
            
            editor.CursorPosition = selectionStart;
        }
        catch (Exception ex)
        {
            Debug.WriteLine($"Ошибка при применении форматирования: {ex.Message}");
        }
    }
    
    private void ApplyFormatting(string text, TextDecorations decorations)
    {
        var span = new Span
        {
            Text = text,
            TextDecorations = decorations
        };
        
        var currentText = editor.Text;
        var selectionStart = editor.CursorPosition;
        var selectionLength = editor.SelectionLength;
        
        editor.Text = currentText.Substring(0, selectionStart) +
                     span.Text +
                     currentText.Substring(selectionStart + selectionLength);
        
        editor.CursorPosition = selectionStart;
    }
}