# encoding: UTF-8
# This file is auto-generated from the current state of the database. Instead
# of editing this file, please use the migrations feature of Active Record to
# incrementally modify your database, and then regenerate this schema definition.
#
# Note that this schema.rb definition is the authoritative source for your
# database schema. If you need to create the application database on another
# system, you should be using db:schema:load, not running all the migrations
# from scratch. The latter is a flawed and unsustainable approach (the more migrations
# you'll amass, the slower it'll run and the greater likelihood for issues).
#
# It's strongly recommended that you check this file into your version control system.

ActiveRecord::Schema.define(version: 20160414101118) do

  create_table "products", primary_key: "ProductSN", force: :cascade do |t|
    t.integer  "ProductVendor"
    t.string   "ProductTitle"
    t.string   "ProductNo"
    t.integer  "SellPrice"
    t.integer  "SellPriceCNY"
    t.integer  "ProductQuantity"
    t.string   "ProductIntroduction"
    t.string   "StyleTitleA"
    t.string   "StyleTitleB"
    t.string   "LargeIcon"
    t.string   "SmallIcon"
    t.string   "ProductPhoto1"
    t.string   "ProductPhoto2"
    t.string   "ProductPhoto3"
    t.string   "ProductPhoto4"
    t.string   "ProductPhoto5"
    t.string   "ProductPhoto6"
    t.string   "ProductPhoto7"
    t.string   "ProductPhoto8"
    t.datetime "created_at",          null: false
    t.datetime "updated_at",          null: false
  end

  add_index "products", ["ProductSN"], name: "index_products_on_ProductSN", unique: true

  create_table "vendors", primary_key: "VendorSN", force: :cascade do |t|
    t.string   "VendorTitle"
    t.datetime "created_at",  null: false
    t.datetime "updated_at",  null: false
  end

  add_index "vendors", ["VendorSN"], name: "index_vendors_on_VendorSN", unique: true

end
