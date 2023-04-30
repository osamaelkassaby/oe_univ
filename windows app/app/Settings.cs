using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.IO;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using MySql.Data.MySqlClient;
namespace app
{
    public partial class Settings : UserControl
    {
        MySqlConnection conn = new MySqlConnection("server=localhost;database=oe_univ;UID=root;password=;SslMode=none;");
        String ImageLocation;
        public string fname;
        public string lname;
        public string email;
        public string token;

        public Settings()
        {
            InitializeComponent();
        }

        private void Settings_Load(object sender, EventArgs e)
        {

            label1.Text += fname;
            conn.Open();
            MySqlCommand cmd = new MySqlCommand("SELECT * FROM `users` WHERE `token`=@token", conn);
            cmd.Parameters.AddWithValue("@token", token);
            MySqlDataReader reader = cmd.ExecuteReader();
            if (reader.HasRows)
            {
                while (reader.Read())
                {
                    try
                    {
                        byte[] img = (byte[])reader["img"];
                        MemoryStream ms = new MemoryStream(img);
                        pictureBox1.Image = Image.FromStream(ms);

                        tbEmail.Text = reader["email"].ToString();

                    }
                    catch
                    {
                      //  MessageBox.Show("Erorr");
                    }

                }
            }
            conn.Close();
        }

        private void changeImage_Click(object sender, EventArgs e)
        {
            Form2 frm = new Form2();

            OpenFileDialog ofd = new OpenFileDialog();
            ofd.Filter =  "JPG IMAGE(*.jpg)|*.png";
            if(ofd.ShowDialog() == DialogResult.OK)
            {
                ImageLocation = ofd.FileName.ToString();
            }
            byte[] img = null;
            FileStream stream = new FileStream(ImageLocation , FileMode.Open, FileAccess.Read);
            BinaryReader binaryReader= new BinaryReader(stream);
            img = binaryReader.ReadBytes((int)stream.Length);
            conn.Open();
            MySqlCommand cmd = new MySqlCommand("UPDATE `users` SET `img` = @img WHERE token = @token",conn);
            cmd.Parameters.AddWithValue("@img", img);
            cmd.Parameters.AddWithValue("@token" , token);
            cmd.ExecuteNonQuery();
            MessageBox.Show("Done");
            conn.Close() ;

        }

        private void save_Click(object sender, EventArgs e)
        {
            conn.Open();
            MySqlCommand cmd = new MySqlCommand("UPDATE `users` SET `email` = @email  , `password` = @password WHERE `token` = @token AND `password` = @oldpassword", conn);

            cmd.Parameters.AddWithValue("@email", tbEmail.Text);
            cmd.Parameters.AddWithValue("@token", token);
            cmd.Parameters.AddWithValue("@password", tbNewPass.Text);
            cmd.Parameters.AddWithValue("@oldpassword", tbOldpass.Text);
            cmd.ExecuteNonQuery();
            MessageBox.Show("Done");
            conn.Close();
        }
    }
}
