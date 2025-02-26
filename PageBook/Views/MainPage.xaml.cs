using PageBook.Models;
using PageBook.ViewModels;

namespace PageBook.Views;

public partial class MainPage : ContentPage
{
	public MainPage()
    {
        InitializeComponent();
        BindingContext = new MainViewModel(Navigation);
    }
    private async void OnNoteSelected(object sender, SelectionChangedEventArgs e)
    {
        if (e.CurrentSelection != null)
        {
            var note = (Note)e.CurrentSelection.FirstOrDefault();
            if (note != null)
            {
                await ((MainViewModel)BindingContext).EditNoteAsync(note);
                ((ListView)sender).SelectedItem = null;
            }
        }
    }
}

