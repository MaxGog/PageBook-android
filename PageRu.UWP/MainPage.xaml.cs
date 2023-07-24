using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;

namespace PageRu.UWP
{
    public sealed partial class MainPage
    {
        public MainPage()
        {
            this.InitializeComponent();

            LoadApplication(new PageRu.App());
        }
    }
}
