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

    protected override async void OnAppearing()
    {
        base.OnAppearing();
        await ((MainViewModel)BindingContext).LoadNotesAsync();
    }
    private async void OnNoteSelected(object sender, SelectedItemChangedEventArgs e)
    {
        if (e.SelectedItem != null)
        {
            if (e.SelectedItem as Note != null)
            {
                await ((MainViewModel)BindingContext).EditNoteAsync((Note)e.SelectedItem);
                ((ListView)sender).SelectedItem = null;
            }
        }
    }

    private void SearchBarTextChanged(object sender, TextChangedEventArgs e)
    {
    }
}

