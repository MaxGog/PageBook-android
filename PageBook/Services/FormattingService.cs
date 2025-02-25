namespace PageBook.Services;

public class FormattingService
{
    private readonly Dictionary<string, Action<string>> formattingCommands;
    private readonly Editor editor;

    [Obsolete]
    public FormattingService()
    {
        editor = Application.Current.MainPage.FindByName<Editor>("noteEditor");
        formattingCommands = new Dictionary<string, Action<string>>
        {
            { "bold", text => ApplyFormatting(text, FontAttributes.Bold) },
            { "italic", text => ApplyFormatting(text, FontAttributes.Italic) },
            { "underline", text => ApplyFormatting(text, TextDecorations.Underline) }
        };
    }

    public void ApplyFormatting(string selectedText, string formatType)
    {
        if (formattingCommands.TryGetValue(formatType, out var formatter))
        {
            formatter(selectedText);
        }
    }

    private void ApplyFormatting(string text, FontAttributes attributes)
    {
        var span = new Span
        {
            Text = text,
            FontAttributes = attributes
        };
        editor.Text += span.Text;
    }

    private void ApplyFormatting(string text, TextDecorations decorations)
    {
        var span = new Span
        {
            Text = text,
            TextDecorations = decorations
        };
        editor.Text += span.Text;
    }
}