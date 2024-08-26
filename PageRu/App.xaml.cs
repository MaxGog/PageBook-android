using System;
using System.IO;
using Xamarin.Forms;

using PageRu.Data;
using PageRu.Views;

namespace PageRu
{
    public partial class App : Application
    {
        public static PageDB pageDB;
        public static PageDB PageDB
        {
            get
            {
                if (pageDB == null)
                {
                    pageDB = new PageDB(Path.Combine(Environment.GetFolderPath(Environment.SpecialFolder.LocalApplicationData), "PagesDataBase.db3"));
                }
                return pageDB;
            }
        }
        public App()
        {
            InitializeComponent();

            MainPage = new AppShell();
        }

        protected override void OnStart()
        {
        }

        protected override void OnSleep()
        {
        }

        protected override void OnResume()
        {
        }
    }
}
