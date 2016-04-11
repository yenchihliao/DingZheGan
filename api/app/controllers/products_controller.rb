require 'net/http'
require 'digest'

class ProductsController < ApplicationController
  before_action :set_product, only: []

  # GET /products
  # GET /products.json
  def index
    @products = Product.all

    render json: @products
  end

  # GET /products/1
  # GET /products/1.json
  def show
    render json: set_product(params[:ProductSN].to_i)
    # render json: @product
  end

  # POST /products
  # POST /products.json
  def update
  end

  private

    def set_product
      @product = Product.find(params[:ProductSN])
    end

    def get_product(id)
      unix_time = Time.now.to_i

      md5 = Digest::MD5.new
      md5.update unix_time.to_s + 'kikirace'
      key = md5.hexdigest

      uri = URI('http://kikistore.csmuse.com/kikistore/api/kikirace_getProductDetail.php')
      params = { :ProductSN => id, :Language => 2, :Time => unix_time, :Key => key}
      uri.query = URI.encode_www_form(params)

      res = Net::HTTP.get(uri).force_encoding("UTF-8")
      hash = JSON.parse(res[2..-1].to_s)
    end

    def create_update_vendor(id, name)
      if Vendor.exists?(VendorSN: id)
      else
        Vendor.create(:VendorSN => id, :VendorTitle => name)
      end
    end

    def create_update_product(id, vendorID, vendorName)
      hash = get_product(id)["Product"][0]

      if Product.exists?(ProductSN: id)
        p = Product.find(id)
        p.update(:ProductSN => id, :ProductVendor => vendorID, :ProductTitle => hash["ProductTitle"], :ProductNo => hash["ProductNo"], :SellPrice => hash["SellPrice"], :SellPriceCNY => hash["SellPriceCNY"], :ProductQuantity => hash["ProductQuantity"], :ProductIntroduction => hash["ProductIntroduction"], :StyleTitleA => hash["StyleTitleA"], :StyleTitleB => hash["StyleTitleB"], :LargeIcon => hash["LargeIcon"], :SmallIcon => hash["SmallIcon"], :ProductPhoto1 => hash["ProductPhoto1"], :ProductPhoto2 => hash["ProductPhoto2"], :ProductPhoto3 => hash["ProductPhoto3"], :ProductPhoto4 => hash["ProductPhoto4"], :ProductPhoto5 => hash["ProductPhoto5"], :ProductPhoto6 => hash["ProductPhoto6"], :ProductPhoto7 => hash["ProductPhoto7"], :ProductPhoto8 => hash["ProductPhoto8"])
      else
        create_update_vendor(vendorID, vendorName)
        Product.create(:ProductSN => id, :ProductVendor => vendorID, :ProductTitle => hash["ProductTitle"], :ProductNo => hash["ProductNo"], :SellPrice => hash["SellPrice"], :SellPriceCNY => hash["SellPriceCNY"], :ProductQuantity => hash["ProductQuantity"], :ProductIntroduction => hash["ProductIntroduction"], :StyleTitleA => hash["StyleTitleA"], :StyleTitleB => hash["StyleTitleB"], :LargeIcon => hash["LargeIcon"], :SmallIcon => hash["SmallIcon"], :ProductPhoto1 => hash["ProductPhoto1"], :ProductPhoto2 => hash["ProductPhoto2"], :ProductPhoto3 => hash["ProductPhoto3"], :ProductPhoto4 => hash["ProductPhoto4"], :ProductPhoto5 => hash["ProductPhoto5"], :ProductPhoto6 => hash["ProductPhoto6"], :ProductPhoto7 => hash["ProductPhoto7"], :ProductPhoto8 => hash["ProductPhoto8"])
    end

    def parse_products
      File.readlines('../assets/products').each do |line|
        columns = line.split(' ')
        vendorSN = columns[0].to_i
        vendorTitle = columns[1]
        productSN = columns[2].to_i

        create_update_product(productSN, vendorSN, vendorTitle)
      end
    end
end
